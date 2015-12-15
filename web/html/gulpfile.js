var var gulp = require('gulp'),
    concat = require('gulp-concat'),
    uglify = require('gulp-uglify'),
    rename = require('gulp-rename'),
    del = require('del');


gulp.task('js',function(){
	return gulp.src("*.js")
	    .pipe(concat('bundle.js'))
		.pipe(rename({suffix:'min'}))
		.pipe(uglify());
});

gulp.task('clean',function(){
	return del(['*.min.js']);
});

gulp.task('default',['clean'],function(){
	gulp.start('js');
});
