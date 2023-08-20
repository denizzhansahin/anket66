package com.bogazliyan.anket66

data class ItemsViewModel(val text: String?, val cevaplar:List<String>,var cevapSirasi:List<String>,val collectionID : String = "", var documentID : String = "",var kullaniciCevabi : String = "") {

}