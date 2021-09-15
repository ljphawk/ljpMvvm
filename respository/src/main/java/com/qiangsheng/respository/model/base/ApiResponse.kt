package com.qiangsheng.respository.model.base

data class ApiResponse<T>(
    var result: Int = 0,
    var error_code: String? = null,
    var message: String? = null,
    var data: T? = null
) {

    constructor() : this(result = 0)

    fun isSuccessful() = result == 0
}