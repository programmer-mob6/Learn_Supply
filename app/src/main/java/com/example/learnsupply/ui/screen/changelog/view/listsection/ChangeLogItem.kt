package com.example.learnsupply.ui.screen.changelog.view.listsection

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.apiservices.data.model.supplier.ChangeLogEntity
import com.example.learnsupply.ui.screen.changelog.model.ChangeLogCallback
import com.example.learnsupply.ui.screen.changelog.uistate.ChangeLogUiState
import com.tagsamurai.tscomponents.card.AdaptiveCardItem
import com.tagsamurai.tscomponents.chip.Chip
import com.tagsamurai.tscomponents.model.Severity
import com.tagsamurai.tscomponents.model.TypeChip
import com.tagsamurai.tscomponents.textfield.UserRecord
import com.tagsamurai.tscomponents.theme.theme
import com.tagsamurai.tscomponents.utils.Spacer.heightBox
import com.tagsamurai.tscomponents.utils.Spacer.widthBox
import com.tagsamurai.tscomponents.utils.Utils.ifNullOrBlank
import com.tagsamurai.tscomponents.utils.bodyStyle
import com.tagsamurai.tscomponents.utils.itemGap4
import com.tagsamurai.tscomponents.utils.titleStyle

@Composable
fun ChangeLogItem(
//    uiState: ChangeLogUiState,
    item: ChangeLogEntity,
//    cb: ChangeLogCallback,
) {
    AdaptiveCardItem {
        Column {
            Chip(
                label = item.action,
                type = TypeChip.BULLET,
                severity = when(item.action) {
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
                Row {
                    Text(
                        text = item.oldValue.ifNullOrBlank { "-" },
                        style = bodyStyle,
                    )
                    4.widthBox()
                    Icon(
                        painter = painterResource(id = com.tagsamurai.tscomponents.R.drawable.ic_arrow_right_line_24dp),
                        contentDescription = null,
                        tint = theme.bodyText,
                        modifier = Modifier.size(12.dp)
                    )
                    4.widthBox()
                    Text(
                        text = item.newValue.ifNullOrBlank { "-" },
                        style = bodyStyle,
                    )
                }
            }

            Row {
                Text(
                    text = item.lastModified,
                    style = bodyStyle,
                    color = theme.bodyText
                )
                Spacer(modifier = Modifier.weight(1f))
                UserRecord(username = item.modifiedBy)
            }
        }
    }
}