import pandas as pd

paraquet_file = r'C:\Users\deepak.mathpal\Downloads\userdata1.parquet'
print(pd.read_parquet(paraquet_file, engine='auto'))
