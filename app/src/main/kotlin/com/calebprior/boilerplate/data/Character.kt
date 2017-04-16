package com.calebprior.boilerplate.data

import com.google.gson.annotations.SerializedName


class Character(
        var name: String,
        var height: String,
        var mass: String,

        @SerializedName("hair_color")
        var hairColor: String,

        @SerializedName("skin_color")
        var skinColor: String,

        @SerializedName("eye_color")
        var eyeColor: String,

        @SerializedName("birth_year")
        var birthYear: String,

        var gender: String,
        var homeworld: String,
        var films: List<String>,
        var species: List<String>,
        var vehicles: List<String>,
        var starships: List<String>
)