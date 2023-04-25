<!-- ABOUT -->
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



<!-- GETTING STARTED -->
## Getting Started

You should have Java 11 and maven on your system

### Prerequisites

#### Install JAVA 11
#### Install Maven


### Installation

1. Clone the repo
   ```sh
   git clone https://github.com/SoniaMalhotraQA/MercedesBenzQAAutomationChallenge.git
   ```
2. Install dependencies
   ```sh
   mvn clean install
   ```



<!-- USAGE EXAMPLES -->
## Usage

* Run Test (default Browser will be chrome)
```sh
mvn clean test
   ```
* Run Tests on Edge
 ```sh
mvn clean test -Dbrowser=edge
   ```
* Run Tests on chrome
 ```sh
mvn clean test -Dbrowser=chrome
