package com.behad.auth

class NotFoundException : Exception()
class BackendErrorException(errorBody: String?) : Exception(errorBody)
class NotImplementedException : Exception()
