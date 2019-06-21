#! /bin/bash

spark-submit --master local[2] --class za.co.shantanu.fadnis.Loans ./target/Loans-1.0.0.jar "inputPath->file:///home/shantanufadnis/codes/projects/datasets/loans.csv" "outputPath->Output"