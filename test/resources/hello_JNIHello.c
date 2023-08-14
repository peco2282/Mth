//
// Created by peco2282 on 2023/04/17.
//

#include "hello_JNIHello.h"
//#include
JNIEXPORT void JNICALL
Java_hello_JNIHello_main(JNIEnv * env, jclass cls, jobjectArray args)
{
printf("Hello, World!\n");
}
