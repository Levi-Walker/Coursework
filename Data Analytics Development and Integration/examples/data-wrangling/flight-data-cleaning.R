# Levi Walker
# Class Exercise
# Feb 3

library(dplyr)
# Load Data from CSV
dataFile <- read.csv(file.choose(), header=T, na.strings = '')
print(dataFile)

dim(dataFile)

# Sample three-fifths of the source data
lw_sampleDF <- data.frame(dataFile) |>
  slice_sample(prop = 3/5)
dim(lw_sampleDF)


# List column names
colnames(lw_sampleDF)

# Remove unused columns
lw_sampleDF <- select(lw_sampleDF, -WHEELS_ON, -WHEELS_OFF)
colnames(lw_sampleDF)

# Rename the carrier column to airline
lw_sampleDF <- lw_sampleDF |>
  rename(AIRLINE = CARRIER)

# Trim whitespace from destination and origin states
lw_sampleDF$DEST_STATE <- trimws(lw_sampleDF$DEST_STATE, "both")
lw_sampleDF$ORIGIN_STATE <- trimws(lw_sampleDF$ORIGIN_STATE, "both")

# Filter Southwest Airlines records
WN_DF <- filter(lw_sampleDF, AIRLINE == "WN")


colnames(lw_sampleDF)
# Select flight date, origin city, and destination city
select(lw_sampleDF, FL_DATE, ORIGIN_CITY, DEST_CITY)


# Select relevant fields for cancelled flights
select(filter(lw_sampleDF, CANCELLED == 1), FL_DATE, ORIGIN, DEST, AIRLINE, CANCELLATION_CODE,CANCELLED)


# Select schedule and route fields for flights between New York airports
select(filter(lw_sampleDF, DEST_STATE == 'NY' & ORIGIN_STATE == 'NY'), FL_DATE, AIRLINE, DEST, ORIGIN_STATE, DEST_STATE, CRS_DEP_TIME, CRS_ARR_TIME)

# Select Friday Delta flights to Miami
select(filter(lw_sampleDF, DAY_NAME == "Friday" & DEST_CITY  == "Miami" & AIRLINE == "DL"), FL_DATE, AIRLINE, DAY_NAME, ORIGIN, DEST)

# Select flights between JFK or LGA and Florida
select(filter(lw_sampleDF, ((ORIGIN == "JFK" | ORIGIN == "LGA") & DEST_STATE == "FL") | ((DEST == "JFK" | DEST == "LGA")  & ORIGIN_STATE == "FL")), FL_DATE, AIRLINE, ORIGIN, DEST, DEST_CITY, CRS_DEP_TIME)

# Select flights longer than 2,200 miles and sort by distance
select(filter(lw_sampleDF[order(lw_sampleDF$DISTANCE),], DISTANCE > 2200), FL_DATE, AIRLINE, ORIGIN, DEST, DISTANCE)

# Create a data frame for flights lasting at least six hours

longFlights <- lw_sampleDF |> 
  select(FL_DATE, AIRLINE, ORIGIN, DEST, TAXI_IN, TAXI_OUT, AIR_TIME) |>
  mutate(FLIGHT_TIME = TAXI_IN + TAXI_OUT + AIR_TIME) |>
  filter(FLIGHT_TIME >= 6*60)

longFlights

# Calculate the average departure delay for non-cancelled JFK flights
mean(lw_sampleDF$DEP_DELAY[lw_sampleDF$CANCELLED == 0 & lw_sampleDF$ORIGIN == "JFK"], na.rm = TRUE)

# Count New York departures by day of the week
lw_sampleDF |>
  filter(ORIGIN_STATE == "NY") |>
  count(DAY_NAME, sort = TRUE)

# Identify the five most frequently represented airlines
lw_sampleDF |>
  count(AIRLINE, sort = TRUE) |>
  head(5)
