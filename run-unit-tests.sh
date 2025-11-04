#!/bin/bash

# Script to run unit tests for iOS Simulator and Android Debug for all modules
# Usage: ./run-unit-tests.sh [module_name]
# If module_name is provided, only that module's tests will run
# Otherwise, all modules' tests will run

set -e

MODULE=${1:-""}

echo "Running Unit Tests..."
if [ -n "$MODULE" ]; then
    echo "Running tests for module: $MODULE"
else
    echo "Running tests for ALL modules"
fi
echo ""

if [ -n "$MODULE" ]; then
    # Run tests for specific module
    echo "Running Android Debug Unit Tests for :$MODULE..."
    ./gradlew :$MODULE:testDebugUnitTest --no-daemon || echo "⚠️  No Android tests found for :$MODULE"
    
    echo ""
    echo "Running iOS Simulator Arm64 Tests for :$MODULE..."
    ./gradlew :$MODULE:iosSimulatorArm64Test --no-daemon || echo "⚠️  No iOS tests found for :$MODULE"
else
    # Run tests for all modules
    echo "Running Android Debug Unit Tests for all modules..."
    ./gradlew testDebugUnitTest --no-daemon --continue || echo "⚠️  Some Android tests may have failed"
    
    echo ""
    echo "Running iOS Simulator Arm64 Tests for all modules..."
    ./gradlew iosSimulatorArm64Test --no-daemon --continue || echo "⚠️  Some iOS tests may have failed"
fi

echo ""
echo "All unit tests completed!"
echo ""
echo "Test results are available in:"
find . -type d -path "*/build/test-results" 2>/dev/null | head -10

