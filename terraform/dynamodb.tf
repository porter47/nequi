resource "aws_dynamodb_table" "franchises" {
  name         = var.franchises_table_name
  billing_mode = "PAY_PER_REQUEST"
  hash_key     = "HASH_KEY"
  range_key    = "SORT_KEY"

  attribute {
    name = "HASH_KEY"
    type = "S"
  }

  attribute {
    name = "SORT_KEY"
    type = "S"
  }
}
