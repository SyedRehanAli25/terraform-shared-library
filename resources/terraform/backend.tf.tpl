terraform {
  backend "s3" {
    bucket         = "REPLACE_ME"
    key            = "REPLACE_ME"
    region         = "REPLACE_ME"
    dynamodb_table = "REPLACE_ME"
    encrypt        = true
  }
}
