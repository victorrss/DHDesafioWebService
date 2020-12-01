package br.com.eaglecode.desafiowebservice.model

data class HQResponse(
    var count: Int,
    val limit: Int,
    var offset: Int,
    var results: ArrayList<Result>,
    val total: Int
)