#指定cmake最小支持版本
cmake_minimum_required(VERSION 3.4.1)





file(GLOB SOURCE src/main/cpp/*.cpp )
# SHARED 指动态库 STATIC指静态库
add_library(
             dnplayer
             SHARED
            ${SOURCE} )


#设置变量 指向ffmpeg静态库目录
#set(my_lib_path ${CMAKE_SOURCE_DIR}/src/main/cpp/libs/${ANDROID_ABI})
#添加静态库 并设置库路径
#add_library(avcodec STATIC IMPORTED)
#set_target_properties(avcodec PROPERTIES IMPORTED_LOCATION ${my_lib_path}/libavcodec.a)
#add_library(avfilter STATIC IMPORTED)
#set_target_properties(avfilter PROPERTIES IMPORTED_LOCATION ${my_lib_path}/libavfilter.a)
#add_library(avformat STATIC IMPORTED)
#set_target_properties(avformat PROPERTIES IMPORTED_LOCATION ${my_lib_path}/libavformat.a)
#add_library(avutil STATIC IMPORTED)
#set_target_properties(avutil PROPERTIES IMPORTED_LOCATION ${my_lib_path}/libavutil.a)
#add_library(swresample STATIC IMPORTED)
#set_target_properties(swresample PROPERTIES IMPORTED_LOCATION ${my_lib_path}/libswresample.a)
#add_library(swscale STATIC IMPORTED)
#set_target_properties(swscale PROPERTIES IMPORTED_LOCATION ${my_lib_path}/libswscale.a)

set(my_lib_path ${CMAKE_SOURCE_DIR}/src/main/cpp/libs/${ANDROID_ABI})
set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -L${my_lib_path} ")


include_directories(src/main/cpp/include)



target_link_libraries(
                       dnplayer
                       avfilter
                       avformat
                       avcodec
                       avutil
                       swresample
                       swscale
                      # ffmpeg
                       z
                        OpenSLES
                        android
                       log )