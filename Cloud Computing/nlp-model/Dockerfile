# Gunakan base image resmi Python
FROM python:3.9-slim

# Set environment variables
ENV PYTHONDONTWRITEBYTECODE=1
ENV PYTHONUNBUFFERED=1
ENV PORT=8080 

# Install dependencies sistem yang dibutuhkan
RUN apt-get update && apt-get install -y \
    gcc \
    libpq-dev \
    --no-install-recommends && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*

# Buat direktori kerja
WORKDIR /app

# Salin semua file ke dalam image
COPY . /app

# Install pipenv dan dependencies Python
RUN pip install --no-cache-dir --upgrade pip && \
    pip install --no-cache-dir -r requirements.txt

# Expose port aplikasi (untuk local testing)
EXPOSE 8080

# Jalankan aplikasi menggunakan Gunicorn
CMD exec gunicorn --bind :$PORT main:app --workers 2 --threads 8 --timeout 0