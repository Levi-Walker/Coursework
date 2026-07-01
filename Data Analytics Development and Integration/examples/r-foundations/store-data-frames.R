# Levi Walker
# January 27
# Class Assignment


# Create the initial store data frame
df <- data.frame(
  Store = c("NY1", "NY2", "NY3", "NY4", "NY5"),
  Age = c(10, 8, 5, 12, 17),
  Employees = c(3, 6, 5, 3, 2),
  Profit2024 = c(100, 189, 127, 130, 22)
)

df

# Access store names
df$Store

# Inspect the third store's 2024 profit
df[3 , c("Store", "Profit2024")]

# Inspect 2024 profit for all stores
df[, c("Store", "Profit2024")]

# Inspect the NY2 store record
df[df$Store == "NY2",]

# Find the largest 2024 profit
max(df$Profit2024)

# Inspect NY4's 2024 profit
df[df$Store == "NY4", c("Store", "Profit2024")]

# Create the New Jersey store data frame
df2 <- data.frame(
  Store = c("NJ1", "NJ2", "NJ3"),
  Age = c(11, 9, 5),
  Employees = c(1, 6, 2),
  Profit2024 = c(14, 92, 38)
)

df2

# Combine the store data frames
All_Stores <- rbind(df,df2)
All_Stores

# Add 2025 profit values
All_Stores$Profit2025 <- c(108, 200, 137, 128, 25, 15, 93, 40)
All_Stores$Profit2025

# Calculate summary statistics
min(All_Stores$Profit2024)
max(All_Stores$Profit2024)
median(All_Stores$Profit2024)
mean(All_Stores$Profit2024)


min(All_Stores$Profit2025)
max(All_Stores$Profit2025)
median(All_Stores$Profit2025)
mean(All_Stores$Profit2025)

# Convert profit values to dollars
All_Stores$Profit2024 <- All_Stores$Profit2024 * 1000
All_Stores$Profit2025 <- All_Stores$Profit2025 * 1000
All_Stores$Profit2024
All_Stores$Profit2025

All_Stores

# Identify the store with the highest 2025 profit
All_Stores[which(All_Stores$Profit2025 == max(All_Stores$Profit2025)),]
All_Stores$Store[which.max(All_Stores$Profit2025)]

# Visualize annual profit by store
# Bar plot of Profit2024
barplot(
  All_Stores$Profit2024,      
  names.arg = All_Stores$Store,
  col = "blue",              
  main = "Profit 2024 by Store",
  ylab = "Profit (2024)",       
  xlab = "Store"
)

# Bar plot of Profit2025
barplot(
  All_Stores$Profit2025,      
  names.arg = All_Stores$Store,
  col = "orange",              
  main = "Profit 2025 by Store",
  ylab = "Profit (2025)",       
  xlab = "Store"
)

# Add store types
All_Stores$store_type <- sample(c("online", "physical"), size = nrow(All_Stores), replace = TRUE)
All_Stores$store_type 
