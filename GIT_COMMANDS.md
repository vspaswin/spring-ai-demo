# Git Commands Guide

## Initial Setup

```bash
# Initialize new repository
git init

# Add remote repository (replace with your GitHub username)
git remote add origin https://github.com/vspaswin/spring-ai-demo.git
```

## Basic Operations

```bash
# Check repository status
git status

# Add all files
git add .

# Create initial commit
git commit -m "Initial commit: Spring Boot REST API with Docker setup"

# Push to main branch
git push -u origin main
```

## Working with Branches

```bash
# Create and switch to new branch
git checkout -b feature/user-management

# Switch between branches
git checkout main
git checkout feature/user-management

# Push new branch to remote
git push -u origin feature/user-management
```

## Updating Repository

```bash
# Get latest changes
git pull origin main

# Update specific branch
git pull origin feature/user-management
```

## Managing Changes

```bash
# View changes
git diff

# Discard changes in working directory
git checkout -- .

# Remove files from staging area
git reset HEAD file.txt

# Commit with detailed message
git commit -m "feat: Add user management functionality

- Add User entity
- Implement CRUD operations
- Add Docker support"
```

## Best Practices

### Commit Messages
```bash
# Feature
git commit -m "feat: Add user authentication"

# Bug fix
git commit -m "fix: Resolve database connection issue"

# Documentation
git commit -m "docs: Update README with API endpoints"

# Refactor
git commit -m "refactor: Improve error handling"
```

### Before Pushing
```bash
# Review changes
git status
git diff --staged

# Run tests
./mvnw test

# Format code
./mvnw spring-javaformat:apply
```

## Troubleshooting

### Reset Last Commit
```bash
# Soft reset (keep changes)
git reset --soft HEAD^

# Hard reset (discard changes)
git reset --hard HEAD^
```

### Fix Wrong Branch
```bash
# Save changes
git stash

# Switch to correct branch
git checkout correct-branch

# Apply changes
git stash pop
```

### Update Fork
```bash
# Add upstream
git remote add upstream https://github.com/original/spring-ai-demo.git

# Fetch and merge updates
git fetch upstream
git merge upstream/main
```

## GitHub Specific

```bash
# Clone repository
git clone https://github.com/vspaswin/spring-ai-demo.git

# Create pull request (using GitHub CLI)
gh pr create --title "Feature: User Management" --body "Implements basic user CRUD operations"

# Review pull request
gh pr list
gh pr checkout 123
```

## Git Configuration

```bash
# Set user information
git config --global user.name "Your Name"
git config --global user.email "your.email@example.com"

# Set default branch to main
git config --global init.defaultBranch main
``` 