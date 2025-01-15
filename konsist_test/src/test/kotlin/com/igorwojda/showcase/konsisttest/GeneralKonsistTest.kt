package com.igorwojda.showcase.konsisttest

import com.lemonappdev.konsist.api.Konsist
import com.lemonappdev.konsist.api.ext.list.properties
import com.lemonappdev.konsist.api.verify.assertFalse
import com.lemonappdev.konsist.api.verify.assertTrue
import org.junit.jupiter.api.Test

// Check General coding rules.
class GeneralKonsistTest {
    @Test
    fun `package name must match file path`() {
        Konsist.scopeFromProject()
            .packages
            .assertTrue { it.hasMatchingPath }
    }

    @Test
    fun `no field should have 'm' prefix`() {
        Konsist.scopeFromProject()
            .classes()
            .properties()
            .assertFalse {
                val secondCharacterIsUppercase = it.name.getOrNull(1)?.isUpperCase() ?: false
                it.name.startsWith('m') && secondCharacterIsUppercase
            }
    }

    @Test
    fun `no class should use Android util logging`() {
        Konsist.scopeFromProject()
            .files
            .assertFalse { it.hasImportWithName("android.util.Log") }
    }

    @Test
    fun `no empty files allowed`() {
        Konsist.scopeFromProject()
            .files
            .assertFalse { it.text.isEmpty() }
    }
}
