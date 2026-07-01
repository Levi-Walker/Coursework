library(rvest)
library(dplyr)
library(ggplot2)
library(stringr)


# Task 1
# Scrape rental listings
url <- "https://dallas.craigslist.org/search/apa"
page <- read_html(url)
  
# Extract listing fields
Description <- page |>
  html_elements(".title") |>
  html_text()

Price <- page |>
  html_elements(".price") |>
  html_text()

Location <- page |>
  html_elements(".location") |>
  html_text()

# Create the rental data frame
LW_rentals_DF <- data.frame(Description, Price, Location)

# Clean and convert rental prices
# Remove currency symbols and separators
LW_rentals_DF$Price <- substring(LW_rentals_DF$Price, 2)

LW_rentals_DF$Price <- sub(",", x=LW_rentals_DF$Price, replacement="")

# Convert prices to numeric values
LW_rentals_DF <- LW_rentals_DF |>
  mutate(Price = as.numeric(Price))

# Confirm the numeric type
typeof(LW_rentals_DF$Price)

# Confirm there are no missing values
summary(LW_rentals_DF)



# Task 2

# Retrieve exchange-rate data
url <- "https://x-rates.com/table/?from=USD&amount=1"
page1 <- read_html(url)
theTables <- html_table(page1)
length(theTables)

RateTable <- theTables[[2]]

colnames(RateTable) <- c("Currancy", "FromUSD", "ToUSD")

LW_RatesDF <- data.frame(RateTable)

summary(LW_RatesDF)


xrate <- filter(LW_RatesDF, Currancy == "Mexican Peso") |>
  pull(FromUSD)



# Task 3:

# Add rental prices in Mexican pesos

LW_rentals_DF <- LW_rentals_DF |>
  mutate(PriceMex = Price * xrate)

# Categorize rentals into price bands

LW_rentals_DF <- LW_rentals_DF |>
  mutate(PriceGroup = case_when(
    Price < 1000 ~ "Under 1000",
    Price <= 1500 ~ "1000-1500",
    Price <= 2000 ~ "1500-2000",
    Price > 2000 ~ "over 2000"
  ))

# Create a one-bedroom rental subset

smallRentals_DF <- LW_rentals_DF |>
  filter(str_detect(Description, "1 bed|1 Bed|1/bd|1/BD"))



# Task 4

# Visualize the rental-price distribution
ggplot(LW_rentals_DF,aes(x=PriceMex)) +
  geom_density() +
  labs(title = 'Distribution of Rental Prices', x = "Price in Pesos", y = "Density")


# Compare rental counts across price groups
ggplot(LW_rentals_DF,aes(x=PriceGroup,fill = PriceGroup)) +
  geom_bar() +
  labs(title = 'Number of Rentals By Price Group', x = "Price in Pesos", y = "Count")


# Visualize one-bedroom rental prices by price group

ggplot(smallRentals_DF,aes(x=Price,fill = PriceGroup)) +
  geom_histogram(bins = 15) +
  labs(title = 'Distribution of 1 Bedroom Rental Prices By Price Group', x = "Price in USD", y = "Count")

