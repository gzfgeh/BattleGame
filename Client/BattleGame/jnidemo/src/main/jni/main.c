//
// Created by sunup_002 on 2015/12/29.
//

/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
#include <android/log.h>

#ifndef LOG_TAG
#define LOG_TAG "ANDROID_LAB"
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR, LOG_TAG, __VA_ARGS__)
#endif

/* Header for class lab_sodino_jnitest_MainActivity */

#ifndef _Included_lab_sodino_jnitest_MainActivity
#define _Included_lab_sodino_jnitest_MainActivity
#ifdef __cplusplus
extern "C" {
#endif
/*
 * Class: lab_sodino_jnitest_MainActivity
 * Method: getStringFromNative
 * Signature: ()Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_com_gzfgeh_jnidemo_MainActivity_getStringFromNative
        (JNIEnv * env, jobject jObj){
    LOGE("log string from ndk.");
    return (*env)->NewStringUTF(env,"Hello From JNI!");
}

#ifdef __cplusplus
}
#endif
#endif



