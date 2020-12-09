#!/bin/bash

 : ${PFINDER_PUBLISH_SERVICE:="http://pfinder-master.jd.com"}
 : ${PFINDER_STORAGE_SERVER:="http://storage.jd.local"}
 : ${PFINDER_STORAGE_BUCKET:="pfinder-agent"}
 : ${PFINDER_HOME:="/export/pfinder"}
 : ${PFINDER_SUGGEST:="stable"}
 : ${PFINDER_VERSION:=`curl -s -f -L "${PFINDER_PUBLISH_SERVICE}/access/suggest/${PFINDER_SUGGEST}"`}
 : ${PFINDER_FORCE_UPDATE:="false"}
 : ${PFINDER_ACCESS_LOG:="false"}

 PFINDER_LIB_PATH=${PFINDER_HOME}/lib
 PFINDER_VERSION_FILE=${PFINDER_LIB_PATH}/version

 print_log() {
     [[ ${PFINDER_ACCESS_LOG} == 'true' ]] && echo $@ || :
 }

 print_error_log() {
     [[ ${PFINDER_ACCESS_LOG} == 'true' ]] && echo "[PFINDER_ACCESS_ERROR]" $@ || :
 }

 CURR_PFINDER_VERSION=`[[ -f "$PFINDER_VERSION_FILE" ]] && cat "$PFINDER_VERSION_FILE"`

 if [[ -z "${PFINDER_VERSION}" ]]; then
     if [[ -z "${CURR_PFINDER_VERSION}" ]]; then
         print_error_log "can't found any usable pfinder agent"
         return 1
     else
         print_error_log "can't get suggest pfinder agent version. will use cached agent version. (${CURR_PFINDER_VERSION})"
         PFINDER_VERSION=${CURR_PFINDER_VERSION}
     fi
 fi

 if [[ -d "${PFINDER_LIB_PATH}" ]]; then
     if [[ -z "${CURR_PFINDER_VERSION}" || "${CURR_PFINDER_VERSION}" != "${PFINDER_VERSION}" || ${PFINDER_FORCE_UPDATE} == 'true' ]]; then
       \rm -rf ${PFINDER_LIB_PATH}
       unset CURR_PFINDER_VERSION
     fi
 fi
 mkdir -p ${PFINDER_LIB_PATH}

 if [[ "${CURR_PFINDER_VERSION:-}" != "${PFINDER_VERSION}" ]]; then
     PFINDER_AGENT_PACKAGE_URL="${PFINDER_STORAGE_SERVER}/${PFINDER_STORAGE_BUCKET}/pfinder-profiler-agent-${PFINDER_VERSION}.tar.gz"
     TEMP_FOLDER="${PFINDER_HOME}/temp"
     mkdir -p ${TEMP_FOLDER}
     PACKAGE_SAVE_PATH="${TEMP_FOLDER}/agent_${PFINDER_VERSION}.tar.gz"
     print_log "Download pfinder-agent package from '$PFINDER_AGENT_PACKAGE_URL'"
     if `curl -s -f -L "${PFINDER_AGENT_PACKAGE_URL}" --output "${PACKAGE_SAVE_PATH}"`; then
         tar -zxf "${PACKAGE_SAVE_PATH}" -C "$PFINDER_LIB_PATH" \
         && echo ${PFINDER_VERSION} > ${PFINDER_VERSION_FILE} \
         && \rm ${PACKAGE_SAVE_PATH} \
         ; UNPACK_SUCCESS=$?
         if [[ ${UNPACK_SUCCESS} != 0 ]]; then
             print_error_log "unpack pfinder agent package fault."
         fi
     else
         print_error_log "download pfinder agent fault."
     fi
 fi

 PFINDER_AGENT_PATH="$PFINDER_LIB_PATH/pfinder-profiler-agent-${PFINDER_VERSION}.jar"
 if [[ -f ${PFINDER_AGENT_PATH} ]]; then
     print_log "pfinder-agent -> \"${PFINDER_AGENT_PATH}\""
     export PFINDER_AGENT="-javaagent:$PFINDER_AGENT_PATH"
     export JAVA_AGENT_OPTS="${JAVA_AGENT_OPTS:-} ${PFINDER_AGENT}"
 else
     print_error_log "\"${PFINDER_AGENT_PATH}\" not exist"
 fi