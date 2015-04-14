//#npm install gulp gulp-minify-css gulp-concat gulp-uglify gulp-rename del --save-dev
var gulp = require('gulp'),
    minifycss = require('gulp-minify-css'),
    concat = require('gulp-concat'),
    uglify = require('gulp-uglify'),
    rename = require('gulp-rename'),
    del = require('del');

gulp.task('default', function() {
    return gulp.src('js/controller.js')
        .pipe(concat('controller.js'))            //合并所有js到main.js
        .pipe(gulp.dest('dist'))                  //输出controller.js到dist文件夹
        .pipe(rename({suffix: '.bundle'}))        //rename压缩后的文件名
        .pipe(uglify())                           //压缩
        .pipe(gulp.dest('dist'));                 //输出
});
