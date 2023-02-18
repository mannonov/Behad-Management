package com.behad.auth.auth.model

import com.behad.auth.auth.database.entitiy.UserEntitiy

data class BehadUser(
    val to_char: String? = null,
    val user_age: Int? = null,
    val user_balance: Int? = null,
    val user_capital: String? = null,
    val user_comment: String? = null,
    val user_country: String? = null,
    val user_create_date: String? = null,
    val user_device_id: List<String>? = null,
    val user_id: String,
    val user_name: String? = null,
    val user_password: String? = null,
    val user_phone: String? = null,
    val user_phone_android_version: List<String>? = null,
    val user_phone_brand: String? = null,
    val user_phone_lang: String? = null,
    val user_phone_model: String? = null,
    val user_region: Any? = null,
    val user_servays: Any? = null,
    val user_surname: String? = null,
    val user_who: String? = null,
)

fun BehadUser.mapToUserEntity(): UserEntitiy = UserEntitiy(
    to_char,
    user_age,
    user_balance,
    user_capital,
    user_comment,
    user_country,
    user_create_date,
    user_device_id,
    user_id,
    user_name,
    user_password,
    user_phone,
    user_phone_android_version,
    user_phone_brand,
    user_phone_lang,
    user_phone_model,
    user_region,
    user_servays,
    user_surname,
    user_who,
)
