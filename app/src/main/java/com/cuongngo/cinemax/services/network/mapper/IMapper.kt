package com.cuongngo.cinemax.services.network.mapper

interface IMapper<in I,out O>{
    fun map(input:I):O
}