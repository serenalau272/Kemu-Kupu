use std::path::{Path, PathBuf};

use rocket::fs::NamedFile;

#[macro_use]
extern crate rocket;

mod database;
mod error;
mod structs;

/// Thinking about endpoints
/// GET api/v1/student/{id}, request information about a user, must contain AUTH header with their token.
/// POST api/v1/student/login, to request an access (JWT) token, must attach login details in body
/// DELETE api/v1/student/{id}, to remove a user
/// GET api/v1/highscores, to request a list of all the high scores
/// POST /api/va/highscore, to add a new score - must contain information about the user

/// Return information about a student
#[get("/api/v1/student/<id>")]
async fn get_student(id: String) {
    
}

/// Attempt to login as a student
#[post("/api/v1/student/login")]
async fn login_student() {
    
}

/// Create a new student
#[post("/api/v1/student")]
async fn create_student() {

}

#[delete("/api/v1/student/<id>")]
async fn delete_student(id: String) {

}

#[get("/api/v1/highscores")]
async fn get_highscores() {

}

#[post("/api/v1/highscores")]
async fn add_score() {
    
}

/// Serve docs about the api
#[get("/api/v1/docs")]
fn docs() {
    //TODO
}

/// Returns the current health status of the database
#[get("/health")]
fn health() -> String {
    "Hello, World!".into()
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
    rocket::build().register("/", catchers![not_found]).mount(
        "/",
        routes![
            get_student,
            login_student,
            create_student,
            delete_student,
            get_highscores,
            add_score,
        ],
    )
}
