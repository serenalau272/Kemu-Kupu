#[macro_use] extern crate rocket;

mod database;
mod error;

#[get("/health")]
fn health() -> String {
    "Hello, World!".into()
}


#[launch]
fn rocket() -> _ {
    rocket::build().mount("/", routes![health])
}