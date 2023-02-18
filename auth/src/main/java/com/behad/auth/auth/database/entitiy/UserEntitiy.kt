package com.behad.auth.auth.database.entitiy

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.behad.auth.auth.model.BehadUser

@Entity(tableName = "user_table")
data class UserEntitiy(
    @ColumnInfo(name = "to_char")
    val to_char: String? = null,
    @ColumnInfo(name = "user_age")
    val user_age: Int? = null,
    @ColumnInfo(name = "user_balance")
    val user_balance: Int? = null,
    @ColumnInfo(name = "user_capital")
    val user_capital: String? = null,
    @ColumnInfo(name = "user_comment")
    val user_comment: String? = null,
    @ColumnInfo(name = "user_country")
    val user_country: String? = null,
    @ColumnInfo(name = "user_create_date")
    val user_create_date: String? = null,
    @ColumnInfo(name = "user_device_id")
    val user_device_id: List<String>? = null,
    @PrimaryKey
    val user_id: String,
    @ColumnInfo(name = "user_name")
    val user_name: String? = null,
    @ColumnInfo(name = "user_password")
    val user_password: String? = null,
    @ColumnInfo(name = "user_phone")
    val user_phone: String? = null,
    @ColumnInfo(name = "user_phone_android_version")
    val user_phone_android_version: List<String>? = null,
    @ColumnInfo(name = "user_phone_brand")
    val user_phone_brand: String? = null,
    @ColumnInfo(name = "user_phone_lang")
    val user_phone_lang: String? = null,
    @ColumnInfo(name = "user_phone_model")
    val user_phone_model: String? = null,
    @ColumnInfo(name = "user_region")
    val user_region: Any? = null,
    @ColumnInfo(name = "user_servays")
    val user_servays: Any? = null,
    @ColumnInfo(name = "user_surname")
    val user_surname: String? = null,
    @ColumnInfo(name = "user_who")
    val user_who: String? = null,
)

fun UserEntitiy.mapToBehadUser() = BehadUser(
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
