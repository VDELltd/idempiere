#!/bin/bash
# Cross-platform compatibility (macOS and Linux)

# Determine the OS and set appropriate readlink command
if [[ "$OSTYPE" == "darwin"* ]]; then
    # macOS
    if command -v greadlink >/dev/null 2>&1; then
        READLINK_CMD="greadlink"
    else
        echo "The 'greadlink' command is not installed. Install it with 'brew install coreutils'."
        exit 1
    fi
else
    # Linux
    READLINK_CMD="readlink"
fi

# Set IDEMPIERE_HOME using the appropriate readlink command
IDEMPIERE_HOME=$( dirname "$($READLINK_CMD -f "${BASH_SOURCE[0]}")" )

if [ "$1" = "-h" ] || [ "$1" = "--help" ]; then
    echo "
Usage: ${BASH_SOURCE[0]} [property_file] [migration_folder]

Called without parameters, it tries to get the database connection information from the file $IDEMPIERE_HOME/idempiere.properties.
This requires that install.app or install.console.app has been previously executed in Eclipse.

If the first parameter is a file, then the script tries to obtain the connection information from that properties file.

Optionally, you can pass one or more migration folders at the end to process them.
If no migration folder is passed, it processes the folder $IDEMPIERE_HOME/migration.

When called with parameter -h or --help, it prints this help message."
    exit 0
fi

if [ "$#" -gt 0 ]; then
    if [ -s "$1" ]; then
        PROPFILE="$(dirname "$1")/$(basename "$1")"
        shift
    fi
fi

if [ -z "$PROPFILE" ]; then
    if [ -s "$IDEMPIERE_HOME/idempiere.properties" ]; then
        PROPFILE="$IDEMPIERE_HOME/idempiere.properties"
    fi
fi

if [ -z "$PROPFILE" ]; then
    echo "There is no idempiere.properties in folder $IDEMPIERE_HOME.
Please run install.app or install.console.app within Eclipse first."
    exit 1
fi

cd "$IDEMPIERE_HOME" || (echo "Cannot cd to $IDEMPIERE_HOME"; exit 1)

CONN=$(grep "^Connection=.*type" "$PROPFILE")
if [ -z "$CONN" ]; then
    echo "There is no Connection definition in the properties file $PROPFILE, or Connection is encrypted."
    exit 1
fi

ADEMPIERE_DB_NAME="$(expr "$CONN" : ".*DBname.=\(.*\),BQ.=")"
ADEMPIERE_DB_SERVER="$(expr "$CONN" : ".*DBhost.=\(.*\),DBport.=")"
ADEMPIERE_DB_PORT="$(expr "$CONN" : ".*DBport.=\(.*\),DBname.=")"
ADEMPIERE_DB_USER="$(expr "$CONN" : ".*UID.=\(.*\),PWD.=")"
if [ "$ADEMPIERE_DB_USER" = "" ]; then
    ADEMPIERE_DB_USER="$(expr "$CONN" : ".*UID.=\(.*\)\]")"
fi
ADEMPIERE_DB_PASSWORD="$(expr "$CONN" : ".*PWD.=\(.*\)]")"
if [ "$ADEMPIERE_DB_PASSWORD" = "" ]; then
    ADEMPIERE_DB_PASSWORD="$( "$IDEMPIERE_HOME/org.adempiere.server-feature/utils.unix/getVar.sh" ADEMPIERE_DB_PASSWORD )"
fi
ADEMPIERE_DB_PATH="$(expr "$CONN" : ".*type.=\(.*\),DBhost.=")"
ADEMPIERE_DB_PATH=$(echo "$ADEMPIERE_DB_PATH" | tr '[:upper:]' '[:lower:]')  # Convert to lowercase

export IDEMPIERE_HOME
export ADEMPIERE_DB_NAME
export ADEMPIERE_DB_SERVER
export ADEMPIERE_DB_PORT
export ADEMPIERE_DB_USER
export ADEMPIERE_DB_PASSWORD
export ADEMPIERE_DB_PATH

bash "org.adempiere.server-feature/utils.unix/$ADEMPIERE_DB_PATH/SyncDB.sh" "$ADEMPIERE_DB_USER" "$ADEMPIERE_DB_PASSWORD" "$ADEMPIERE_DB_PATH" "$@"
