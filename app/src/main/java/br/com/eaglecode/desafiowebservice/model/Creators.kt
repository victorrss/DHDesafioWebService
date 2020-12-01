package br.com.eaglecode.desafiowebservice.model

data class Creators(
    val available: Int,
    val collectionURI: String,
    val items: List<Item>,
    val returned: Int
)