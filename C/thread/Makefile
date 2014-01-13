CC=gcc
MKDIR=mkdir -p
RMDIR=rm -Rf
CFLAGS=-W -Wall -lpthread

BUILD_DIR=build/

all: clean run

#.PHONY: directories clean build

$(BUILD_DIR):
	@echo "Creating $(BUILD_DIR) folder"
	@$(MKDIR) $(BUILD_DIR)

directories: $(BUILD_DIR)

build: directories
	@echo "Compiling sources"
	@$(CC) snippet.c -o $(BUILD_DIR)snippet $(CFLAGS)

run: build
	@echo "Running snippet"
	@$(BUILD_DIR)snippet

clean:
	@echo "Deleting $(BUILD_DIR) folder"
	@$(RMDIR) $(BUILD_DIR)
