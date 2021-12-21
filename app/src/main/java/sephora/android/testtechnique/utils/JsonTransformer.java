package sephora.android.testtechnique.utils;

public interface JsonTransformer {

    @Throws(IOException::class, ApiMapperException::class)
    fun <T> fromJson(json: String, classToTransformTo: TypeToken<T>): T

    @Throws(IOException::class, ApiMapperException::class)
    fun <T> fromJson(json: ByteArray, classToTransformTo: TypeToken<T>): T

    @Throws(ApiMapperException::class)
    fun toJson(source: Any): String
}

    inline fun <reified T> JsonTransformer.fromJson(json: String): T = fromJson(json, TypeToken.of(T::class.java))
class ApiMapperException : Exception {
        constructor(cause: Exception) : super("Failed mapping body of request/response", cause)
        constructor(message: String) : super(message)
        }
