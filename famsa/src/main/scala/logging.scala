package com.rho.scrap

object FamsaLogging {

  import com.rho.scrap.FamsaClasses.{Page,Item,concat}
  import com.rho.file.quickFunc.{writeToFile,readIntoList}

  val datadir = "data/"
  val category_file = datadir+"famsa_categories"
  val product_file = datadir+"famsa_products"

  private def concatPages(a: String, b: Page) = concat("\n")(a,b.toString)
  private def concatItems(a: String, b: Item) = concat("\n")(a,b.toString)

  def savePages(pages: List[Page]): Unit = {
    writeToFile(category_file,pages.foldLeft[String]("")(concatPages),encoding="UTF-8")
  }

  def saveItems(products: List[Item]): Unit = {
    import java.io.File
    val string = {
      if (products.isEmpty) throw new Error("Empty product list")
      else products.foldLeft[String]("")(concatItems)
    }
    if ((new File(product_file)).exists) {
      writeToFile(product_file,products.head.header+"\n"+string,encoding="UTF-8")
    } else {
      writeToFile(product_file,string,encoding="UTF-8",append=true)
    }
  }

  def readPagesFile: List[Page] = readIntoList(category_file,encoding="UTF-8").map(new Page(_))

}