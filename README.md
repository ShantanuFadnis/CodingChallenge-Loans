# Coding Challenge.
The project is aimed to calculate aggregated loan amounts and count of loans issued per network, product and month to the users.

## Getting Started
#### Output expectations:
Output should be a tuple of network, product and month displaying aggregated loans and counts.

#### How to run?
* Clone the repository.
* Build the jar file using maven. 
  
  `mvn package`
* Run the Spark submit command on a cluster with Apache Spark up and running OR run the project in any IDE (Eclipse, Intellij IDEA).
  
  IF THE FILE EXISTS ON LOCAL FILE SYSTEM
  ```
  spark-submit --master local[2] --class za.co.shantanu.fadnis.Loans Loans-1.0.0.jar "inputPath->file:///path-to-file" "outputPath->Output.csv"
  ```
  ELSE IF THE FILE IS ON HDFS
  ```
  spark-submit --master local[2] --class za.co.shantanu.fadnis.Loans Loans-1.0.0.jar "inputPath->path-to-file" "outputPath->Output.csv"
  ```

#### Assumptions Made
* Amounts in the file are of same currency.
* Single quotes are removed around the field values since they don't represent any meaningful value.
* Output doesn't contain users since the focus is on the loans and count of loans issued per month, product and through each network.
* Apache Spark is used as the problem statement suggested to treat the scenario as if it were to be executed in PRODUCTION environment. The input file shared contains only 8 records, but the actual file is expected to have millions of records and processing it through Spark would be much faster. Due to Apache Spark's optimizer on query handling and in memory parallel processing.
* Apache Spark has been proved to work efficiently with large amounts of data in a big data cluster environment.
* The reason behind using Scala API of Apache Spark is that Apache Spark itself is written in Scala and hence the APIs available in Scala work much efficiently as compared to Python.

#### Language & Framework
 Processing Framework: Apache Spark 2.2.0+
 Programming Language: Scala
 Build Tool: Apache Maven
 Version Control: Git

#### Author
Shantanu Fadnis
