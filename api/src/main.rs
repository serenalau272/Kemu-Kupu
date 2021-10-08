use std::path::{Path, PathBuf};

use rocket::fs::NamedFile;

#[macro_use]
extern crate rocket;
#[macro_use]
extern crate diesel;

mod common;
mod error;
mod models;
mod schema;

use diesel::prelude::*;
use rocket::http::Status;
use rocket::serde::json::Json;
/// Database connection
#[rocket_sync_db_pools::database("postgres_database")]
struct UsersDbConn(diesel::PgConnection);

/// Thinking about endpoints
/// GET api/v1/student/{id}, request information about a user, must contain AUTH header with their token.
/// POST api/v1/student/login, to request an access (JWT) token, must attach login details in body
/// DELETE api/v1/student/{id}, to remove a user
/// GET api/v1/highscores, to request a list of all the high scores
/// POST /api/va/highscore, to add a new score - must contain information about the user

/// TODO General todos
/// implement JWT system
/// Move common DB requests (such as looking up a user) into a framework under common.rs to avoid duplicate code.

/// Return information about a student
#[get("/api/v1/student/<search_id>", format = "application/json")]
async fn get_student(search_id: i32, conn: UsersDbConn) -> (Status, String) {
    //TODO authentication here

    //Load the item from the db, if it exists
    use crate::schema::users::dsl::*;
    let r: Option<crate::models::User> = conn
        .run(move |c| {
            let r = users
                .filter(id.eq(search_id))
                .limit(1)
                .load::<crate::models::User>(c);
            if let Ok(mut v) = r {
                if v.is_empty() {
                    return None;
                }
                return Some(v.remove(0));
            }
            return None;
        })
        .await;

    // Format and return
    if let Some(user) = r {
        let json_response = models::Response { data: user }.to_json();
        if let Err(e) = json_response {
            return (Status::InternalServerError, e.to_string());
        }
        return (
            Status::Ok,
            json_response.unwrap(), //Safety: Checked above with if let Err();
        );
    }
    (
        Status::BadRequest,
        models::Response {
            data: "User Not Found",
        }
        .to_json()
        .unwrap(),
    )
}

/// Attempt to login as a student
#[post(
    "/api/v1/student/login",
    data = "<login_information>",
    format = "application/json"
)]
async fn login_student(
    conn: UsersDbConn,
    login_information: Json<models::UserCredentials>,
) -> (Status, String) {
    let login_information = login_information.into_inner();
    //Check if the user exists in the db
    use crate::schema::users::dsl::*;
    let name = login_information.usr.clone();
    let r: Option<crate::models::User> = conn
        .run(move |c| {
            let r = users
                .filter(usr.eq(name))
                .limit(1)
                .load::<crate::models::User>(c);
            if let Ok(mut v) = r {
                if v.is_empty() {
                    return None;
                }
                return Some(v.remove(0));
            }
            return None;
        })
        .await;

    if r.is_none() {
        return (
            Status::BadRequest,
            models::Response {
                data: "Incorrect Password or Username",
            }
            .to_json()
            .unwrap(),
        );
    }

    //Check that their password hash matches
    let hash_valid = match common::compare_hashed_strings(login_information.pwd, r.unwrap().pwd) {
        Ok(h) => h,
        Err(e) => {
            return (
                Status::InternalServerError,
                models::Response {
                    data: format!("Failed to compare hashes {}", e.to_string()),
                }
                .to_json()
                .unwrap(),
            )
        }
    };

    if ! hash_valid {
        return (
            Status::BadRequest,
            models::Response {
                data: "Incorrect Password or Username"
            }
            .to_json()
            .unwrap(),
        );
    }

    //TODO return useful login token here, for now just tell them they're cool.
    return (
        Status::Created,
        models::Response { data: "Logged In" }.to_json().unwrap(),
    );
}

/// Create a new student
#[post("/api/v1/student", data = "<new_user>", format = "application/json")]
async fn create_student(
    conn: UsersDbConn,
    new_user: Json<models::UserCredentials>,
) -> (Status, String) {
    //Check their password meets minimum requirements
    let new_user = new_user.into_inner();
    if new_user.pwd.len() < 8 {
        return (
            Status::BadRequest,
            models::Response {
                data: "Password Too Short",
            }
            .to_json()
            .unwrap(),
        );
    }

    //Check that the username isnt't taken
    use crate::schema::users::dsl::*;
    let name = new_user.usr.clone();
    let r: Option<crate::models::User> = conn
        .run(move |c| {
            let r = users
                .filter(usr.eq(name))
                .limit(1)
                .load::<crate::models::User>(c);
            if let Ok(mut v) = r {
                if v.is_empty() {
                    return None;
                }
                return Some(v.remove(0));
            }
            return None;
        })
        .await;

    if r.is_some() {
        return (
            Status::BadRequest,
            models::Response {
                data: "Username Taken",
            }
            .to_json()
            .unwrap(),
        );
    }

    //Hash password
    let hashed_password = match common::hash_string_with_salt(new_user.pwd) {
        Ok(p) => p,
        Err(e) => {
            return (
                Status::InternalServerError,
                models::Response {
                    data: format!("Unable to hash password {}", e.to_string()),
                }
                .to_json()
                .unwrap(),
            )
        }
    };

    let new_user = models::UserCredentials {
        usr: new_user.usr,
        pwd: hashed_password,
    };

    //Save account in db
    use schema::users;
    let r: Result<models::User, diesel::result::Error> = conn
        .run(move |c| {
            diesel::insert_into(users::table)
                .values(new_user)
                .get_result(c)
        })
        .await;

    if let Err(e) = r {
        return (
            Status::InternalServerError,
            models::Response {
                data: format!("Failed to insert into server {}", e.to_string()),
            }
            .to_json()
            .unwrap(),
        );
    }

    //TODO Return valid token
    return (
        Status::Created,
        models::Response {
            data: "Account Created",
        }
        .to_json()
        .unwrap(),
    );
}

#[delete("/api/v1/student/<user_id>", format = "application/json")]
async fn delete_student(user_id: i32, conn: UsersDbConn) -> (Status, String) {
    //TODO: Authentication

    //Delete student from db
    use crate::schema::users::dsl::*;
    let r: Result<crate::models::User, diesel::result::Error> = conn.run(move |c| {
        diesel::delete(users.filter(id.eq(user_id)))
            .get_result(c)
    }).await;

    if let Err(e) = r {
        return (
            Status::InternalServerError,
            models::Response {
                data: format!("Unable to delete user due to error {}", e.to_string()),
            }
            .to_json()
            .unwrap(),
        );
    }

    return (
        Status::Ok,
        models::Response {
            data: format!("Account {} deleted", r.unwrap().usr),
        }
        .to_json()
        .unwrap(),
    )
}

#[get("/api/v1/highscores", format = "application/json")]
async fn get_highscores(conn: UsersDbConn) -> (Status, String) {
    //TODO allow params for pagination
    use crate::schema::scores::dsl::*;
    let r: Result<Vec<models::Score>, diesel::result::Error> = conn.run(move |c| {
        scores
            .limit(50)
            .load::<crate::models::Score>(c)
    }).await;
    if let Err(e) = r {
        return (
            Status::InternalServerError,
            models::Response {
                data: format!("Failed to query the server due to error {}", e.to_string()),
            }
            .to_json()
            .unwrap(),
        )
    }
    return (
        Status::Ok,
        models::Response {
            data: r.unwrap()
        }
        .to_json()
        .unwrap()
    )
}

#[post("/api/v1/highscores", data = "<new_score>", format = "application/json")]
async fn add_score(new_score: Json<models::NewScore>, conn: UsersDbConn) {
    //TODO authentication
    
    
}

/// Serve docs about the api
#[get("/api/v1/docs")]
fn docs() {
    //TODO
}

/// Returns the current health status of the database
#[get("/health")]
fn health() -> String {
    "Good".into()
}

/// Handle the serving of any static resources for various pages
/// SAFETY: Rocket has a neat implementation preventing a path from getting outside of /static - keeping our host safe.
#[get("/static/<file..>")]
async fn website_resource(file: PathBuf) -> Option<NamedFile> {
    NamedFile::open(Path::new("static/").join(file)).await.ok()
}

/// Handle any 404's
#[catch(404)]
async fn not_found() -> Option<NamedFile> {
    NamedFile::open("static/www/404.html").await.ok()
}

#[launch]
fn rocket() -> _ {
    rocket::build()
        .register("/", catchers![not_found])
        .mount(
            "/",
            routes![
                get_student,
                login_student,
                create_student,
                delete_student,
                get_highscores,
                add_score,
                website_resource,
                health,
            ],
        )
        .attach(UsersDbConn::fairing())
}
