package com.cuongngo.cinemax.services.network.invoker

import java.io.IOException

class ApiException(message: String) : IOException(message)
class NoInternetException(message: String) : IOException(message)