/*
 * Copyright (c) 2023 Airbyte, Inc., all rights reserved.
 */

package io.airbyte.integrations.destination.iceberg.glue;

import io.airbyte.integrations.destination.iceberg.config.format.DataFileFormat;

public class IcebergGlueCatalogS3AvroIntegrationTest extends BaseIcebergGlueCatalogS3IntegrationTest {

  @Override
  DataFileFormat fileFormat() {
    return DataFileFormat.AVRO;
  }

}
