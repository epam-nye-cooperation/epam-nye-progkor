#!/usr/bin/env bash

function main() {
  local pom_path="${1}"
  local patch="${2}"

  local project_version
  project_version="$(extract_project_version "${pom_path}")"

  local major_minor
  major_minor="$(extract_semver_major_minor_part "${project_version}")"

  printf "%s.%s" "${major_minor}" "${patch}"
}

function extract_project_version() {
  local pom_path="${1}"
  mvn help:evaluate -Dexpression=project.version --quiet -DforceStdout --file "${pom_path}"
}

function extract_semver_major_minor_part() {
  local semantic_version="${1}"
  echo "${semantic_version}" | cut -d'-' -f 1 | awk -F. '{print $1"."$2}'
}

main "$@"
