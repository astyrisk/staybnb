# Start from the official Jenkins Long Term Support (LTS) image
FROM jenkins/jenkins:lts

# Switch to the root user to install system packages
USER root

# 1. Update package list and install prerequisites
RUN apt-get update && apt-get install -y \
    wget \
    gnupg \
    ca-certificates \
    && rm -rf /var/lib/apt/lists/*

# 2. Add Google's official GPG key and repository, then install Chrome
RUN wget -q -O - https://dl.google.com/linux/linux_signing_key.pub | gpg --dearmor -o /usr/share/keyrings/google-chrome-keyring.gpg && \
    echo "deb [arch=amd64 signed-by=/usr/share/keyrings/google-chrome-keyring.gpg] http://dl.google.com/linux/chrome/deb/ stable main" | tee /etc/apt/sources.list.d/google-chrome.list && \
    apt-get update && \
    apt-get install -y google-chrome-stable && \
    # Clean up the apt cache to keep your Docker image size down
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*

# Drop back to the regular 'jenkins' user (crucial for Jenkins security and file permissions)
USER jenkins