package com.damanjit.tflliveroadstatus.networkimport okhttp3.OkHttpClientimport okhttp3.logging.HttpLoggingInterceptorimport retrofit2.Retrofitimport retrofit2.converter.scalars.ScalarsConverterFactoryimport retrofit2.http.GETimport retrofit2.http.Pathimport retrofit2.http.Queryimport retrofit2.http.QueryMapprivate const val BASE_URL = "https://api.tfl.gov.uk"val interceptor : HttpLoggingInterceptor = HttpLoggingInterceptor().apply {    level = HttpLoggingInterceptor.Level.BODY}val client : OkHttpClient = OkHttpClient.Builder().apply {    addInterceptor(interceptor)}.build()private val retrofit = Retrofit.Builder()    .addConverterFactory(ScalarsConverterFactory.create())    .baseUrl(BASE_URL)    .client(client)    .build()// Function is marked suspend because it wil be called from a coroutine inside the viewmodel class.interface TfLApiService {    @GET("/Road/{roadName}")    suspend fun getRoadStatus(@Path("roadName")roadName: String)}object TfLApi {    val retrofitService : TfLApiService by lazy {        retrofit.create(TfLApiService::class.java)    }}