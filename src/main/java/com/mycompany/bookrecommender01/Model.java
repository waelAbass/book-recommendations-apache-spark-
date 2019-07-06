/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.bookrecommender01;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.ml.evaluation.RegressionEvaluator;
import org.apache.spark.ml.recommendation.ALS;
import org.apache.spark.ml.recommendation.ALSModel;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

/**
 *
 * @author AbdrhmnAns
 */
public class Model {

    public static void main(String[] args) {
        SparkSession session = SparkSession.builder()
                .appName("BookRecommender")
                .master("local[*]")
                .config("spark.sql.warehouse.dir", "file:///E://")
                .getOrCreate();

        Dataset<Row> dataset = session
                .read().option("header","true")
                .csv("G:\\dataset1.csv");
        dataset.show();
        //dataset.show();
        //data.write().json("E:\\jsondata");
        ////Dataset<Row> mydata = session.read().json("D:\\jsondata\\m.json");
        ////mydata.show();
        //preprocessing
        JavaRDD<Rating> javaRDD = dataset.toJavaRDD().map(new Function<Row, Rating>() {
            @Override
            public Rating call(Row t1) throws Exception {
                          //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
               //  System.out.println("here "+t1.toString());
                String[] fields = t1.toString().split(";");
               //  System.out.println("userid "+fields[0]);
                String userId = fields[0];
                String ISBN = fields[1];
                String rate = fields[2];
                Rating r =new Rating();
                r.setUserId(userId);
                r.setRate(rate);
                r.setISBN(ISBN);
                return r;
            }

        });
        Dataset<Row> ratingDataset = session.createDataFrame(javaRDD, Rating.class);
      //  ratingDataset.write().json("E://cvb");
        ratingDataset.show();
    }
}
