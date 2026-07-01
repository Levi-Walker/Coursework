# February 19, 2026
# Relational data analysis

# install.packages("nycflights13")
data(package="nycflights13")
library("nycflights13")
library(dplyr)


airlineDF <- airlines
airportDF <- airports
flightDF <- flights
planeDF <- planes
weatherDF <- weather

# Key: carrier
airlineDF

# Key: faa
airportDF

# Key: tailnum + time_hour
flightDF

# Key: tailnum
planeDF

# Key: time_hour + origin
weatherDF


# Keys were evaluated individually for duplicates and missing values.
# Check for duplicate keys
dupeKeys <- weatherDF |>
  count(time_hour, origin)|>
  filter(n != 1)
dupeKeys

# Check for missing keys
missingKey <- weatherDF |>
  filter(is.na(origin) | is.na(origin))
missingKey



# Add a surrogate key for flights
flightDF_key <- flightDF |>
  mutate(
    flight_id = row_number(), .before =1
  )

flightDF_key


myDF <- flightDF_key |>
  select(flight_id, flight, year, time_hour, origin, dest, tailnum,
         carrier) |>
  left_join(airlineDF, by = "carrier")
  
myDF

# Join departure records with temperature and wind-speed observations
depart_weather <- flightDF_key |>
  select(flight, carrier, tailnum, time_hour, dep_time) |>
  left_join(weatherDF, by = "time_hour") |>
  select(flight, carrier, tailnum, time_hour, dep_time, temp, wind_speed)
depart_weather

airlineDF
planeDF

# Identify airlines operating aircraft with fewer than 100 seats
small_planes <- airlineDF |>
  left_join(flightDF) |>
  left_join(planeDF) |>
  filter(seats < 100) |>
  select(carrier, name, manufacturer, model, seats)
small_planes

# Identify flights whose aircraft are missing from the fleet data
missing_planes <- flightDF |>
  anti_join(planeDF, by = "tailnum") |>
  select(flight, tailnum)
missing_planes

