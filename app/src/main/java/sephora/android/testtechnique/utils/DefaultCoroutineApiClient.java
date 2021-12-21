package sephora.android.testtechnique.utils;

public class DefaultCoroutineApiClient(
) :CoroutineApiClient{
        override suspend fun fetchResult(request:ApiRequest):ApiResponseResult=
        withContext(ioDispatcher){
        try{
        with(request.perform()){
        ApiResponseResult.Response(code,body?.byteStream())
        }
        }catch(e:IOException){
        ApiResponseResult.NetworkError(e)
        }
        }
