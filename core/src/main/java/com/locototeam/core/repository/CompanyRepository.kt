package com.locototeam.core.repository

import javax.inject.Inject

class CompanyRepository @Inject constructor(
    private val companyServiceDataSource: ICompanyService,
): ICompanyService by companyServiceDataSource