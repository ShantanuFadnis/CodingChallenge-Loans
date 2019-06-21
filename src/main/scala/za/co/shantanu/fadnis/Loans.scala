package za.co.shantanu.fadnis

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._
import za.co.shantanu.fadnis.utils.ArgumentParser

// Case class to model the input data in a proper standardized structure.
case class Loans(msisdn: String, network: String, curr_date: String, product: String, amount: Double)

object Loans {
  def main(args: Array[String]): Unit = {
    // Create a Spark Session to initialize the job.
    val spark = SparkSession.builder().appName("Loans Aggregation").getOrCreate()

    // Create a Spark Context to read the text file from the file system.
    val sc = spark.sparkContext
    import spark.implicits._

    // Parse arguments to validate.
    val opts = ArgumentParser.parseArgs(args)

    // Load the text file from the file system or HDFS.
    val loans = sc.textFile(opts("inputPath"))

    // Filter the text file to exclude header and any blank lines.
    val filteredLoans = loans.filter(x => !(x.startsWith("MSI") || x.length == 0))

    // Model the file contents into a case class to align data into a tabular format (DataFrame) and cast columns according to their definition.
    val loansDF = filteredLoans.map(x => x.split(",")).map {
      case Array(msisdn, network, date, product, amount) => Loans(msisdn.replace("'", ""), network.replace("'", ""), date.replace("'", ""), product.replace("'", ""), amount.toDouble)
    }.toDF

    // Perform data aggregation and grouping.
    val aggregatedLoans = loansDF
      .select(col("network"),
        col("product"),
        month(to_date(col("curr_date"), "dd-MMM-yyyy")) as "month",
        col("amount")
      )
      .groupBy(col("network"),
        col("product"),
        col("month")
      )
      .agg(sum(col("amount")) as "total_loan",
      count(col("network")) as "count").orderBy(col("network"),
      col("product"), col("month")
    )

    // Store the DataFrame in HDFS under a given output path.
//    aggregatedLoans.coalesce(1).write.csv(opts("outputPath"))

    // Convert the DataFrame into a tuple like structure (as per the requirement).
    val tupledFormat = aggregatedLoans.rdd.map(x => (x(0).toString, x(1).toString, x(2).toString, x(3).toString.toDouble, x(4).toString.toInt))

    // Store the output tuple generated in step above.
    tupledFormat.coalesce(1).saveAsTextFile(opts("outputPath"))
  }
}
