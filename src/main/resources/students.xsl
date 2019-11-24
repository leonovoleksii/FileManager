<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version = "1.0" xmlns:xsl = "http://www.w3.org/1999/XSL/Transform">

    <xsl:template match="/">
        <html>
            <body>
                <table border="1" style="font-size: 18pt; margin: auto">
                    <xsl:for-each select="database/specialty/group/student">
                        <tr><td colspan="2" style="text-align:center; background-color:grey">Student</td></tr>
                        <tr>
                            <td>Name</td>
                            <td><xsl:value-of select="name"/></td>
                        </tr>
                        <tr>
                            <td>Surname</td>
                            <td><xsl:value-of select="surname"/></td>
                        </tr>
                        <tr>
                            <td>Phone number</td>
                            <td><xsl:value-of select="phone"/></td>
                        </tr>
                        <tr>
                            <td>City</td>
                            <td><xsl:value-of select="city"/></td>
                        </tr>
                        <tr>
                            <td>Group</td>
                            <td><xsl:value-of select="../@ID"/></td>
                        </tr>
                        <tr>
                            <td>Specialty</td>
                            <td><xsl:value-of select="../@NAME"/></td>
                        </tr>
                    </xsl:for-each>
                </table>
            </body>
        </html>
    </xsl:template>
</xsl:stylesheet>
