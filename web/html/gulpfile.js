var gulp = require('gulp'),
    concat = require('gulp-concat'),
    uglify = require('gulp-uglify'),
    rename = require('gulp-rename'),
    del = require('del');


gulp.task('js',function(){
	return gulp.src("src/*.js")
	    //.pipe(concat('bundle.js'))
	    //.pipe(gulp.dest('dist'))
		.pipe(rename({suffix:'.min'}))
		.pipe(uglify())
		.pipe(gulp.dest('dist'));
});

gulp.task('clean',function(){
	return del(['dist/*']);
});

gulp.task('default',['clean'],function(){
	gulp.start('js');
});
