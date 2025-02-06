package com.example.learnsupply.ui.screen.changelog.view.listsection

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.apiservices.data.model.supplier.ChangeLogEntity
import com.tagsamurai.tscomponents.card.AdaptiveCardItem
import com.tagsamurai.tscomponents.chip.Chip
import com.tagsamurai.tscomponents.model.Severity
import com.tagsamurai.tscomponents.model.TypeChip
import com.tagsamurai.tscomponents.textfield.UserRecord
import com.tagsamurai.tscomponents.theme.theme
import com.tagsamurai.tscomponents.utils.Spacer.heightBox
import com.tagsamurai.tscomponents.utils.Utils.ifNullOrBlank
import com.tagsamurai.tscomponents.utils.Utils.toDateFormatter
import com.tagsamurai.tscomponents.utils.bodyStyle
import com.tagsamurai.tscomponents.utils.itemGap4
import com.tagsamurai.tscomponents.utils.titleStyle

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ChangeLogItem(
    item: ChangeLogEntity,
) {
    AdaptiveCardItem {
        Column {
            Chip(
                label = item.action,
                type = TypeChip.BULLET,
                severity = when (item.action) {
                    "Delete" -> Severity.DANGER
                    "Edit" -> Severity.WARNING
                    "Create" -> Severity.SUCCESS
                    else -> Severity.WARNING
                }
            )
            itemGap4.heightBox()
            Text(
                text = item.companyName,
                style = titleStyle,
                color = theme.bodyText
            )
            itemGap4.heightBox()
            Text(
                text = item.field,
                style = bodyStyle,
                color = theme.bodyText
            )
            itemGap4.heightBox()
            if (item.action == "Edit") {
                FlowRow(
                    verticalArrangement = Arrangement.Center,
                    maxItemsInEachRow = 3,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = item.oldValue.ifNullOrBlank { "-" },
                        style = bodyStyle,
                    )
                    Box(modifier = Modifier.padding(2.dp)) {
                        Icon(
                            painter = painterResource(id = com.tagsamurai.tscomponents.R.drawable.ic_arrow_right_line_24dp),
                            contentDescription = null,
                            tint = theme.bodyText,
                            modifier = Modifier.size(12.dp)
                        )
                    }
                    Text(
                        text = item.newValue.ifNullOrBlank { "-" },
                        style = bodyStyle,
                    )
                }
                itemGap4.heightBox()
            }

            Row {
                Text(
                    text = item.lastModified.toDateFormatter(),
                    style = bodyStyle,
                    color = theme.bodyText
                )
                Spacer(modifier = Modifier.weight(1f))
                UserRecord(username = item.modifiedBy)
            }
        }
    }
}