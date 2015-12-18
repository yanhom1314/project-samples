const gulp = require('gulp'),
    concat = require('gulp-concat'),
    uglify = require('gulp-uglify'),
	babel = require('gulp-babel'),
	react = require('gulp-react'),
    rename = require('gulp-rename'),
    del = require('del');


// Define some paths.
var paths = {
  js: ['js/*.js']
};


gulp.task('js',function(){
	return gulp.src(paths.js)
		//.pipe(babel())
		.pipe(react())
		.pipe(gulp.dest('dist'))
	    //.pipe(concat('bundle.js'))
        //.pipe(gulp.dest('dist'))
		.pipe(rename({suffix:'.min'}))
		.pipe(uglify())
		.pipe(gulp.dest('dist'));
});

gulp.task('clean',function(){
	return del(['dist/*.js']);
});

gulp.task('default',['clean','js']);
