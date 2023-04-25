# Mercedes-Benz Automation Challenge
This Code solves Mercedes-Benz QA automation challenge using java 11, selenium and maven.

This automation supports chrome and Edge browser.

The selenium test automates the following test case “Validate A Class models price are between
£15,000 and £60,000" as described below:

* Opens Mercedes-benz United Kingdom market
* Under “Our Models” - Select “Model: Hatchbacks”;
* Mouse over the “A Class” model available and proceed to “Build your car”
* Filter by Fuel type “Diesel”
* Takes and saves a screenshot of the results under /results/screenshot
* Saves the value “£” of the highest and lowest price results in /results/resultFile.text



## Prerequisites

Make sure you have Java 11 and maven installed on your machine

Install Java 11
https://www.oracle.com/de/java/technologies/javase/jdk11-archive-downloads.html

Install Maven (Apache Maven 3.6.3 or above)
https://maven.apache.org/install.html


## Getting Started

1. Clone the repo
   ```sh
   git clone https://github.com/SoniaMalhotraQA/MercedesBenzQAAutomationChallenge.git
   ```
2. Run test (Default browser will be chrome)
   ```sh
   mvn clean test
   ```



## Usage

* Run Test (default Browser will be chrome)
```sh
mvn clean test
   ```
* Run Test on Edge
 ```sh
mvn clean test -Dbrowser=edge
   ```
* Run Test on chrome
 ```sh
mvn clean test -Dbrowser=chrome

##
* Screenshot will be saved under /results/screenshot
* Text file with highest and minimum price values will saved in /results/resultFile.text
