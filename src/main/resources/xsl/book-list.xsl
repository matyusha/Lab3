<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="html" omit-xml-declaration="yes"/>
    <xsl:template match="/">
        <html>
            <head>
                <meta charset="UTF-8"/>
                <title>Books</title>
            </head>
            <body>
                <div class="header">
                    <div class="header-link">
                        <a href="/authors" >Authors</a>
                    </div>
                </div>

                <table  border="1">
                    <tr>
                        <td><strong>title</strong></td>
                        <td><strong>year</strong></td>
                        <td><strong>price</strong></td>
                        <td><strong>authors</strong></td>
                    </tr>
                    <xsl:for-each select="ArrayList/item">
                        <tr>
                            <td><xsl:value-of select="title"/></td>
                            <td><xsl:value-of select="year"/></td>
                            <td><xsl:value-of select="price"/></td>
                            <td>
                                <table  border="1">
                                    <xsl:for-each select="./authors/authors">
                                        <tr>
                                            <td><xsl:value-of select="name"/></td>
                                            <td><xsl:value-of select="surname"/></td>
                                        </tr>
                                    </xsl:for-each>
                                </table>
                            </td>
                        </tr>
                    </xsl:for-each>
                </table>
            </body>
        </html>
    </xsl:template>
</xsl:stylesheet>