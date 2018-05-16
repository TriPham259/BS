#!/bin/bash
# This shell script is to hang a string after the name of all files in current directory
# Tri Pham, Huy Tran
# 19.05.2018

# pass the input string as command argument
input_string=$1

# iterate through the files in current directory
for file in $(find . -maxdepth 1 -type f)
do
  # remove from 'file' from right to left until encounter the first '.' (incl. '.') => name  
  name=${file%.*}
  
  # remove from 'file' from left to right until encounter the final '.' (incl. '.') => extension
  extension=${file##*.}
  
  # rename the files using mv
  mv "$file" "${name}${input_string}.${extension}"
done