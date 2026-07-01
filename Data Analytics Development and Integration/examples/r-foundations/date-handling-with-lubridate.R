# Levi Walker
# Dates in R
# Feb 10

# Exercise 1
# install.packages("lubridate")
library(lubridate)

# Convert to Date Objects
a <- "2024-03-15"
b <- "03/15/2024"
c <- "15-03-2024"

bda <- as.Date(a)
bdb <-as.Date(b,format = "%m/%d/%Y")
bdc <-as.Date(c,format = "%d-%m-%Y")

# Parse dates with lubridate
lda <- ymd(a)
ldb <- mdy(b)
ldc <- dmy(c)



# Exercise 2: Base R and lubridate
# Extract day, month, and year
d <- as.Date("2024-02-10")
byd <- as.numeric(format(d, "%Y"))
bmd <- as.numeric(format(d, "%m"))
bdd <- as.numeric(format(d, "%d"))

lyd <- year(d)
lmd <- month(d)
ldd <- day(d)

# Exercise 3: Base R and lubridate date arithmetic
# Set the starting date
stdate <- as.Date("2024-02-10")
# Add 14 days
bad <- stdate + 14

# Equivalent lubridate calculation
lad <- d+days(14)

bnd <- as.Date("2024-03-01")- stdate
lnd <- ymd("2024-03-01") - stdate
print(bnd)
print(lnd)


# Exercise 4: Month arithmetic with lubridate
amld <- ymd("2024-01-31")
lam<- amld %m+% months(1)
print(lam)
