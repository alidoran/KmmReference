import SwiftUI
import shared
import WebKit

class SwiftCallback: ApiResult, ObservableObject {
    @Published var response: String = ""
    @Published var currentUrl: String = ""

    func onResult(response: String) {
        DispatchQueue.main.async {
            self.response = response
            print("Success: \(response)")
        }
    }

    func onUrl(url: String) {
        DispatchQueue.main.async {
            self.currentUrl = url
            print("called:onUrl : \(url)")
        }
    }

    func onSampleComplexDataClass(sampleComplexDataClass: SampleComplexDataClass?){
        DispatchQueue.main.async {
            if let name = sampleComplexDataClass?.name{
                print(name)
                }
        }
    }
}

struct ContentView: View {
    @State private var simpleWebViewShow = false
    @State private var webViewOpenListenerShow = false
    @State private var webViewOpenLoadListenerShow = false
    @State private var textFieldValue = "840"


    let greet = Greeting().greet()
    @ObservedObject var apiStringCallback = SwiftCallback()
    @ObservedObject var apiUrlCallback = SwiftCallback()
    @ObservedObject var apiDataClassCallback = SwiftCallback()

    var body: some View {
    VStack{
//    self.navigationController?.pushViewController(viewController, animated: true)

        MyWebView(callback: apiUrlCallback)
          	  Text(greet)

            Button("Simple Web View"){
                self.simpleWebViewShow.toggle()
            }
            .sheet(isPresented: $simpleWebViewShow){
                SimpleWebView(url: URL(string: "https://www.google.com")!)
            }

            Button("WebView Open Listener"){
                self.webViewOpenListenerShow.toggle()
            }
            .sheet(isPresented: $webViewOpenListenerShow){
                let url = URL(string: "https://www.google.com")!
                WebViewOpenListener(url: url)
            }

            Button("WebView Open Load Listener"){
                self.webViewOpenLoadListenerShow.toggle()
            }
            .sheet(isPresented: $webViewOpenLoadListenerShow){
                WebViewOpenLoadListener(url: URL(string: "https://www.google.com")!)
            }

            Button("Call a callback api result"){
                FakeApi().callApiByCallback(apiResult: apiStringCallback)
            }
            Text(apiStringCallback.response)

            Button("Call a Url Address"){
                FakeApi().callUrlAddressByCallback(apiResult: apiUrlCallback)
            }

            Button("Call a SampleComplexDataClass Api"){
                FakeApi().callSampleComplexDataClassByCallback(apiResult: apiDataClassCallback)
            }
            Text(apiDataClassCallback.response)

            Button("Read text field value"){
                print($textFieldValue)
            }
            TextField("Sample Text Field", text: $textFieldValue)
        }
    }
}

struct ContentView_Previews: PreviewProvider {
	static var previews: some View {
		ContentView()
	}
}

struct SimpleWebView: UIViewRepresentable {
    let url: URL

    func makeUIView(context: Context) -> WKWebView  {
        let webView = WKWebView()
        webView.load(URLRequest(url: url))
        return webView
    }

    func updateUIView(_ uiView: WKWebView, context: Context) {
        // Code to update your view
    }
}

struct WebViewOpenListener: UIViewRepresentable {
    let url: URL

    func makeUIView(context: Context) -> WKWebView  {
        let webView = WKWebView()
        webView.navigationDelegate = context.coordinator
        webView.load(URLRequest(url: self.url))
        return webView
    }

    func updateUIView(_ uiView: WKWebView, context: Context) {
        // Code to update your view
    }

    func makeCoordinator() -> Coordinator {
        Coordinator()
    }

    class Coordinator: NSObject, WKNavigationDelegate {
        func webView(_ webView: WKWebView, didFinish navigation: WKNavigation!) {
            print("Page loaded.")
        }
    }
}

struct WebViewOpenLoadListener: UIViewRepresentable {
    let url: URL

    func makeUIView(context: Context) -> WKWebView  {
    let webView = WKWebView()
        webView.navigationDelegate = context.coordinator
        return webView
    }

    func updateUIView(_ uiView: WKWebView, context: Context) {
        let request = URLRequest(url: self.url)
        uiView.load(request)
    }

    func makeCoordinator() -> Coordinator {
        Coordinator(self)
    }

    class Coordinator: NSObject, WKNavigationDelegate {
        var parent: WebViewOpenLoadListener

        init(_ webView: WebViewOpenLoadListener) {
            self.parent = webView
        }

        func webView(_ webView: WKWebView, didStartProvisionalNavigation navigation: WKNavigation!) {
            print("Start loading the webpage")
        }

        func webView(_ webView: WKWebView, didFinish navigation: WKNavigation!) {
            print("Webpage loading finished")
        }
    }
}

struct MyWebView: UIViewControllerRepresentable {
    @ObservedObject var callback: SwiftCallback
    typealias UIViewControllerType = ViewController

    func makeUIViewController(context: Context) -> ViewController {
    print("called:makeUIViewController")
                let viewController = ViewController()
                viewController.swiftCallback = callback
                return viewController
    }

    func updateUIViewController(_ uiViewController: ViewController, context: Context) {
                if let url = URL(string: callback.currentUrl) {
                    uiViewController.webView.load(URLRequest(url: url))
                }
    }
}

class ViewController: UIViewController, WKScriptMessageHandler, WKNavigationDelegate {
    var webView: WKWebView!
    var swiftCallback: SwiftCallback!

    override func loadView() {
        let webConfiguration = WKWebViewConfiguration()
        let userContentController = WKUserContentController()

        userContentController.add(self, name: "toastMe")
        userContentController.add(self, name: "observe")

        webConfiguration.userContentController = userContentController
        webView = WKWebView(frame: .zero, configuration: webConfiguration)
        webView.navigationDelegate = self
        view = webView
    }

    override func viewDidLoad() {
        super.viewDidLoad()

        if let url = URL(string: swiftCallback.currentUrl) {
            print(url)
            webView.load(URLRequest(url: url))
        }
    }

    func userContentController(_ userContentController: WKUserContentController, didReceive message: WKScriptMessage) {
        if message.name == "toastMe", let messageBody = message.body as? String {
            print("toastMe: \(messageBody)")
        } else if message.name == "observe", let messageBody = message.body as? String {
            print("observe: \(messageBody)")
        }
    }

    func webView(_ webView: WKWebView, didStartProvisionalNavigation navigation: WKNavigation!) {
        print("Page load started")
    }

    func webView(_ webView: WKWebView, didFinish navigation: WKNavigation!) {
        print("Page load finished")
    }
}