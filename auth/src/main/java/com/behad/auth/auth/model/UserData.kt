package com.behad.auth.auth.model

data class UserData(
    val to_char: String,
    val user_age: Int,
    val user_balance: Int,
    val user_capital: String,
    val user_comment: String,
    val user_country: String,
    val user_create_date: String,
    val user_device_id: List<String>,
    val user_id: String,
    val user_name: String,
    val user_password: String,
    val user_phone: String,
    val user_phone_android_version: List<String>,
    val user_phone_brand: String,
    val user_phone_lang: String,
    val user_phone_model: String,
    val user_region: Any,
    val user_servays: Any,
    val user_surname: String,
    val user_who: String,
)
