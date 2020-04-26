<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:fo="http://www.w3.org/1999/XSL/Format">
    <!-- Attribute used for table border -->
    <xsl:attribute-set name="tableBorder">
        <xsl:attribute name="border">solid 0mm black</xsl:attribute>
        <xsl:attribute name="padding">2mm</xsl:attribute>
    </xsl:attribute-set>
    <xsl:template match="index">
        <fo:root>

            <fo:layout-master-set>
                <fo:simple-page-master master-name="simpleA4"
                                       page-height="29.7cm" page-width="21.0cm" margin="1cm" margin-top="2cm">
                    <fo:region-body/>

                </fo:simple-page-master>
            </fo:layout-master-set>
            <fo:page-sequence master-reference="simpleA4">
                <fo:flow flow-name="xsl-region-body">
                    <fo:block font-size="22pt" text-align="center" font-family="Helvetica"  color="white" font-weight="bold" space-after="10mm" padding-top="3mm"
                              padding-bottom="3mm" background-color="#2C3E50" >
                        <xsl:value-of select="heading"/>
                    </fo:block>
                    <fo:block font-size="10pt">
                        <fo:table table-layout="fixed" width="100%" border-collapse="separate">
                            <fo:table-column column-width="2cm"/>
                            <fo:table-column column-width="13cm"/>
                           <!-- <fo:table-column column-width="2cm"/> -->
                            <fo:table-column column-width="2cm"/>
                            <fo:table-header>
                                <fo:table-cell xsl:use-attribute-sets="tableBorder" >
                                    <fo:block font-weight="bold" font-size="12pt">Sno</fo:block>
                                </fo:table-cell>
                                <fo:table-cell xsl:use-attribute-sets="tableBorder" >
                                    <fo:block font-weight="bold" font-size="12pt">Component Name</fo:block>
                                </fo:table-cell>
                                <!--   <fo:table-cell xsl:use-attribute-sets="tableBorder" >
                                      <fo:block font-weight="bold">Page Start</fo:block>
                                  </fo:table-cell> -->
                                  <fo:table-cell xsl:use-attribute-sets="tableBorder" >
                                      <fo:block font-weight="bold" font-size="12pt">Page</fo:block>
                                  </fo:table-cell>
                              </fo:table-header>
                              <fo:table-body>
                                  <xsl:apply-templates select="topic"/>
                              </fo:table-body>
                          </fo:table>
                      </fo:block>
                  </fo:flow>
              </fo:page-sequence>
          </fo:root>
      </xsl:template>

      <xsl:template match="topic">
          <fo:table-row>
              <fo:table-cell xsl:use-attribute-sets="tableBorder" >
                  <fo:block font-weight="bold">
                      <xsl:value-of select="Sno"/>
                  </fo:block>
              </fo:table-cell>

              <fo:table-cell xsl:use-attribute-sets="tableBorder" >
                  <fo:block font-weight="bold">
                      <xsl:value-of select="component-name"/>
                  </fo:block>
                  <fo:block font-style="italic" font-size="9pt">
                      <xsl:value-of select="book-name"/>
                  </fo:block>

              </fo:table-cell>
              <!--   <fo:table-cell xsl:use-attribute-sets="tableBorder" >
                    <fo:block>
                        <xsl:value-of select="page-from"/>
                    </fo:block>
                </fo:table-cell> -->
                <fo:table-cell xsl:use-attribute-sets="tableBorder" >
                    <fo:block font-weight="bold">
                        <xsl:value-of select="page-to"/>
                    </fo:block>
                </fo:table-cell>
            </fo:table-row>
        </xsl:template>
    </xsl:stylesheet>